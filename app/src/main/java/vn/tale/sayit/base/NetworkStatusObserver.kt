package vn.tale.sayit.base

interface NetworkStatusObserver {
  fun onNetworkStatusChanged(isConnected: Boolean)
}
